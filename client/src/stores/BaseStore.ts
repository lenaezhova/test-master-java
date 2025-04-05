import {action, computed, makeObservable, observable} from 'mobx';
import {AnyObject} from "antd/es/_util/type";

const EMPTY_OBJECT: any = {}

export class BaseStore<ItemType extends AnyObject = AnyObject> {
  constructor() {
    makeObservable(this);
  }

  fetchItemsAdapter = (response) => {
    if(typeof response === 'string') {
      return JSON.parse(response);
    }

    return response
  };

  fetchItemsAdapterPagination = response => {
    if(typeof response === 'string') {
      response = JSON.parse(response);
    }

    if (response?.page) {
      this.defaultPageSize = response.page.size;

      return {
        items: response.content,
        total: response.page.totalElements,
        size: response.page.size,
        pages: response.page.totalPages
      }
    }

    return response
  };

  fetchMethod = function (params: any, executor?): Promise<any> {
    throw 'Не задан fetchMethod';
  };

  fetchItemMethod = function (params: any, executor?): Promise<any> {
    throw 'Не задан fetchMethod';
  };

  saveMethod = function (params: any): Promise<any> {
    throw 'Не задан saveMethod';
  };

  fetchFailed = error => {

  };

  fetchItemFailed = error => {

  };

  fetchSuccess = (response: any) => {
  };

  fetchItemSuccess = (response: any, params?: any) => {
    this.setItem(response);
  };

  saveSuccess = (response, params) => {

  };

  saveFailed = error => {
  };

  saveItemAdapter = (response) => {
    if(!response) return;

    if(typeof response === 'string') {
      return JSON.parse(response);
    }

    return response
  };

  syncItems = (store: any) => {
  };

  executor = {cancel: null};
  executorItem = {cancel: null};

  @observable fetchProgress = false;
  @observable fetchError = false;
  @observable fetchErrorText = '';
  @observable fetchDone = false;

  @observable saveProgress = false;
  @observable saveError = false;
  @observable saveErrorText = '';
  @observable saveDone = false;

  @observable items: ItemType[] = [];
  @observable item: ItemType = EMPTY_OBJECT;
  @observable total = 0;
  @observable noResults = false;
  @observable fetchItemProgress = false;
  @observable fetchItemError = false;
  @observable fetchItemDone = false;

  defaultPageSize = 10;
  defaultCurrent = 1;
  pageSizeOptions = [10, 20, 30, 50, 100]

  @computed get noItem() {
    return Object.entries(this.item).length === 0;
  }

  @computed get noItems() {
    return this.items.length === 0;
  }

  @action setItem(item?) {
    this.item = item || EMPTY_OBJECT;
  }


  @computed get noTotalItems() {
    return this.total === 0;
  }

  @action resetApi() {
    this.fetchProgress = false;
    this.fetchError = false;
    this.fetchErrorText = '';
    this.saveError = false;
    this.saveErrorText = '';
    this.fetchDone = false;
    this.saveDone = false;
  }

  @action resetItem() {
    this.item = EMPTY_OBJECT;
  }

  @action fullReset() {
    this.resetApi();
    this.items = [];
    this.resetItem();
    this.total = 0;
    this.noResults = false;
  }

  @action fetchItems(params?) {
    if (this.executor.cancel) {
      this.executor.cancel('user cancel');
    }

    this.fetchProgress = true;
    this.fetchError = false;
    this.fetchErrorText = '';
    this.fetchDone = false;
    let fetchCancel = false;

    return this.fetchMethod(params, this.executor)
      .then(action(response => {
        this.fetchDone = true;
        response = this.fetchItemsAdapter(response);
        this.items = response?.items || [];
        this.total = response?.total || 0;
        this.fetchSuccess(response);
        return response;
      }))
      .catch(action(e => {
        if (e.message == 'user cancel') {
          fetchCancel = true;
          return;
        }

        this.fetchError = true;

        this.fetchFailed(e);

        console.error(e);
      }))
      .finally(action(() => {
        if (!fetchCancel) {
          this.fetchProgress = false;
        }
      }));
  }

  @action fetchItem(params?) {
    if (this.executorItem.cancel) {
      this.executorItem.cancel('user cancel');
    }

    this.fetchProgress = true;
    this.fetchError = false;
    this.fetchErrorText = '';
    this.fetchDone = false;

    this.fetchItemProgress = true;
    this.fetchItemError = false;
    this.fetchItemDone = false;

    let fetchCancel = false;

    return this.fetchItemMethod(params, this.executorItem)
      .then(action(response => {
        this.fetchDone = true;
        response = this.fetchItemsAdapter(response);
        this.fetchItemDone = true;
        this.fetchItemSuccess(response, params);
      }))
      .catch(e => {
        if (e.message == 'user cancel') {
          fetchCancel = true;
          return;
        }

        this.fetchError = true;
        this.fetchItemError = true;

        this.fetchItemFailed(e);
        console.error(`Ошибка в методе "${this.fetchMethod.name}":`, e);
      })
      .finally(() => {
        if (!fetchCancel) {
          this.fetchProgress = false;
          this.fetchItemProgress = false;
        }
      });
  }

  @action saveItem(params) {
    this.saveProgress = true;
    this.saveError = false;
    this.saveDone = false;
    this.saveErrorText = '';

    return new Promise<any>((resolve, reject) => {
      this.saveMethod(params)
        .then(action(response => {
          this.saveDone = true;
          response = this.saveItemAdapter(response);
          this.saveSuccess(response, params);
          resolve(response);
        }))
        .catch(e => {
          this.saveError = true;

          console.error(`Ошибка в методе "${this.saveMethod.name}":`, e);
          this.saveFailed(e);
          reject();
        })
        .finally(() => {
          this.saveProgress = false;
        });
    });
  }
}

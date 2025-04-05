import {inject, observer} from "mobx-react";
import {UserStore} from "./UserStore/UserStore";

const stores = {
  $user: new UserStore(),
}

type AllBaseStores = Partial<typeof stores>;

const createInject = <T extends Record<string, any>>() => {
  // @ts-ignore
  return <K extends keyof T>(storeNames: K[]) => inject(...storeNames);
};

const injectBase = createInject<AllBaseStores>();

export {
  stores,
  createInject,
  injectBase
};

export type { AllBaseStores };


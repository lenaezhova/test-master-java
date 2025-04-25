import React from 'react';
import s from './TestsList.module.scss';
import {useTests} from "../../../../api/tests/query";
import {Button, Spin} from "antd";
import clsx from "clsx";
import {DeleteOutlined} from "@ant-design/icons";
import TestAction from "./components/TestAction/TestAction";

const TestsList = () => {
  const {data, isLoading, isFetching} = useTests();

  return (
    <Spin spinning={isLoading || isFetching}>
      <div className={s.testWrapper}>
        {data?.content?.map(el => (
          <div key={el.id} className={clsx("testBackground", s.test)}>
            <div className={s.testContent}>
              <div className={s.title}>
                <span>Тест</span>
                {el.title}
              </div>
              <div className={s.description}>
                <p>Описание</p>
                {el.description}
              </div>
            </div>
            {!el.deleted && <TestAction id={el.id} />}
          </div>
        ))}
      </div>
    </Spin>
  );
};

export default TestsList;

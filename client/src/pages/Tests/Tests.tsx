import {FC, memo, useState} from "react";
import s from './Tests.module.scss';
import TestsList from "../../fetures/tests/components/TestsList/TestsList";
import TestsManageModalBtn from "../../fetures/tests/components/TestsManageModalBtn/TestsManageModalBtn";


interface TestsProps {}

const Tests: FC<TestsProps> = ({}) => {
  return (
    <div className="container">
      <div className={s.titleWrapper}>
        <div className={s.titleBlock}>
          <h1> Список тестов </h1>
          <TestsManageModalBtn type={'primary'}> Создать тест </TestsManageModalBtn>
        </div>
        <TestsList/>
      </div>
    </div>
  );
};

export default Tests;

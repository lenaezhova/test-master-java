import {FC, memo} from "react";
import s from './Tests.module.scss';
import CreateTestForm from "../../fetures/tests/components/CreateTestForm/CreateTestForm";
import TestsList from "../../fetures/tests/components/TestsList/TestsList";


interface TestsProps {}

const Tests: FC<TestsProps> = ({}) => {
    return (
      <div className="container">
        <h1>Тесты</h1>
        <div className={s.titleWrapper}>
          <h2>Создание теста</h2>
          <CreateTestForm />
        </div>
        <div className={s.titleWrapper}>
          <h2> Список тестов </h2>
          <TestsList/>
        </div>
      </div>
    );
};

export default Tests;

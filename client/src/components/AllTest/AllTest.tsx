import s from "./AllTest.module.scss";
import {FC, memo} from "react";
import {useTests} from "../../api/tests/query";

interface AllTestProps {}

const AllTest = () => {
    const {data} = useTests();

    console.log(data);

    return (
        <div>
            AllTest
        </div>
    );
};

export default memo(AllTest);

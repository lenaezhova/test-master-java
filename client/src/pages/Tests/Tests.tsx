import {FC, memo} from "react";
import AllTest from "../../components/AllTest/AllTest";

interface TestsProps {}

const Tests: FC<TestsProps> = ({}) => {
    return (
        <div className="container">
            <AllTest/>
        </div>
    );
};

export default Tests;

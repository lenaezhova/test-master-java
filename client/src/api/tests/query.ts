import {useQuery} from "react-query";
import {getTests} from "./index";

const useTests = () => {
    return useQuery('tests', getTests)
}

export {
    useTests
}

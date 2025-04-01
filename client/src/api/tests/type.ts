export enum ITestStatus {
    CLOSED = 'CLOSED',
    OPEN = 'OPEN'
}

export interface ITest {
    id: number;
    ownerId?: number;
    status: ITestStatus;
    title: string
    description: string
}

export interface ITestsCreateModelRequest {
    title: string
    description: string
}

export interface ITestsUpdateModelRequest extends ITestsCreateModelRequest {
    id: number;
}

export interface ITestsDeleteModelRequest {
    id: number;
}

import React, {Fragment, useState} from 'react';
import s from './TestsManageModalBtn.module.scss';
import {Button, ButtonProps, Modal} from "antd";
import ManageTestForm from "../ManageTestForm/ManageTestForm";
import {useTest} from "../../../../api/tests/query";

interface TestsManageModalBtnProps extends Omit<ButtonProps, 'id'> {
  id?: number;
}

const TestsManageModalBtn = ({id, ...btnProps}: TestsManageModalBtnProps) => {
  const isEdit = Boolean(id);
  const {refetch, isFetching, isLoading} = useTest(id, {enabled: false});
  const [isOpen, setIsOpen] = useState(false);

  const handleOpen = async () => {
    if (isEdit) {
      await refetch();
    }
    setIsOpen(true);
  }

  const handleClose = () => {
    setIsOpen(false)
  }

  return (
    <Fragment>
      <Button
        {...btnProps}
        loading={isLoading || isFetching}
        onClick={handleOpen}
      />
      <Modal
        destroyOnClose
        open={isOpen}
        onCancel={handleClose}
        footer={null}
      >
        <ManageTestForm
          onSuccess={handleClose}
          onCancel={handleClose}
          id={id}
        />
      </Modal>
    </Fragment>
  );
};

export default TestsManageModalBtn;

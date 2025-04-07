import React from 'react';
import s from "./ConfirmEmailBlock.module.scss";
import Lottie from "lottie-react";
import sendEmailAnimationLottie from "../../animation/sendEmailAnimationLottie.json";
import {AllBaseStores, injectBase} from "../../../../stores/stores";
import {observer} from "mobx-react";
import {Button, message, Row} from "antd";

const ConfirmEmailBlock = injectBase(['$user'])(observer((props: AllBaseStores) => {
  const { $user } = props;
  const isLoading = $user.fetchItemProgress;

  const onCheckEmail = async () => {
    try {
      await $user.fetchItem();
      if ($user.isConfirmEmail) {
        message.success('Email успешно подтвержден!');
      } else {
        message.info('Email не подтвержден, попробуйте еще раз');
      }
    } catch (e) {
      console.log(e);
    }
  }

  return (
    <div className={s.confirmEmailBlock}>
      <Row justify={'center'}>
        <Lottie className={s.sendEmailLottie} animationData={sendEmailAnimationLottie} loop={false} />
      </Row>
      <div className={s.title}>
        Привет! {$user.item.name}
      </div>
      <div className={s.subTitle}>
        Добро пожаловать в Test Master
      </div>
      <div className={s.description}>
        Чтобы воспользоваться сервисом, необходимо подтвердить почту.
        <div>
          Для этого перейдите по ссылке, которая прикреплена к письму.
        </div>
        <div>
          После подтверждения, обновите страницу.
        </div>
      </div>
      <div className={s.confirmBtn}>
        <Button
          onClick={onCheckEmail}
          size={'large'}
          type={'primary'}
          loading={isLoading}
        >
          Проверить подтверждение
        </Button>
      </div>
    </div>
  );
}));

export default ConfirmEmailBlock;

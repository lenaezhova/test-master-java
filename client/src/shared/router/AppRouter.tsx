import {FC} from 'react';
import {Routes, Route, Navigate} from 'react-router-dom'
import {privateRoutes, publicRoutes, RouteNames} from "./index";
import {AllBaseStores, injectBase} from "../../stores/stores";
import {observer} from "mobx-react";
const AppRouter : FC = injectBase(['$user'])(observer((props:AllBaseStores) => {
  const { $user } = props;

  const getRenderRoutes = (routes) => (
    <Routes>
      {routes.map(({path, component: Component}) =>
        <Route
          key={path}
          path={path}
          element={<Component/>}
        />
      )}
      <Route path="*" element={<Navigate to={RouteNames.TESTS} replace/>}/>
    </Routes>
  )

  return (
    $user.isAuth ? getRenderRoutes(privateRoutes) : getRenderRoutes(publicRoutes)
  );
}));

export default AppRouter;

import {FC} from 'react';
import {Routes, Route, Navigate} from 'react-router-dom'
import {privateRoutes, publicRoutes, RouteNames} from "./index";
const AppRouter : FC = () => {
    const isAuth = false;

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
        isAuth ? getRenderRoutes(privateRoutes) : getRenderRoutes(publicRoutes)
    );
};

export default AppRouter;

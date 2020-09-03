import React, {ReactElement, ReactNode, useCallback} from "react";
import {AuthorizedRoute} from "@tshio/react-router-permissions";

interface INotAuthorizedRouteProps {
    path: string;
    children: ReactNode;
    redirect: ReactNode;
}

const NotAuthorizedRoute = (props: INotAuthorizedRouteProps): ReactElement => {

    const authorizationStrategy = useCallback((roles: string[]) => {
        return !roles || roles.length === 0;
    }, []);

    return (
        <AuthorizedRoute path={props.path} authorizationStrategy={authorizationStrategy}>
            {({isAuthorized}) => (isAuthorized ? props.children : props.redirect)}
        </AuthorizedRoute>
    )
}

export default NotAuthorizedRoute;
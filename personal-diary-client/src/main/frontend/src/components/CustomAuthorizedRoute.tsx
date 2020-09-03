import React, {ReactElement, ReactNode} from "react";
import {AuthorizedRoute} from "@tshio/react-router-permissions";

interface ICustomAuthorizedRouteProps {
    path: string;
    requires: string[];
    children: ReactNode;
    redirect: ReactNode;
}

const CustomAuthorizedRoute = (props: ICustomAuthorizedRouteProps): ReactElement => {

    return (
        <AuthorizedRoute path={props.path} requires={props.requires}>
            {({isAuthorized}) => (isAuthorized ? props.children : props.redirect)}
        </AuthorizedRoute>
    )
}

export default CustomAuthorizedRoute;
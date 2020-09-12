import React, {ReactElement, ReactNode, useCallback, useContext} from "react";
import {Redirect, Switch} from "react-router";
import LoginPage from "../pages/login/LoginPage";
import RegistrationPage from "../pages/registration/RegistrationPage";
import DiaryPage from "../pages/diary/DiaryPage";
import {AppContext} from "../security/AppContext";
import CustomAuthorizedRoute from "../components/CustomAuthorizedRoute";
import {ERole, user} from "../model/ERole";
import NotAuthorizedRoute from "../components/NotAuthorizedRoute";
import {usePermissions} from "@tshio/react-router-permissions";
import RecoveryPasswordPage from "../pages/password/RecoveryPasswordPage";

const RootRouter = (): ReactElement => {

    const appContext = useContext(AppContext);

    const {permissions} = usePermissions();

    const getMainRedirectUrl = useCallback((): string => {
        if (permissions) {
            if (permissions.find((permission: string) => permission === ERole.USER)) {
                return '/diary';
            }
            if (permissions.find((permission: string) => permission === ERole.ADMIN)) {
                return '';
            }
            if (!permissions.find((permission: string) => permission === ERole.ADMIN) &&
                permissions.find((permission: string) => permission === ERole.MODERATOR)) {
                return '';
            }
        }
        return '/login';
    }, [permissions]);

    const getRedirect = useCallback((): ReactNode => {
        return appContext.isSignOut ? <Redirect to="/login"/> : <Redirect to={getMainRedirectUrl()}/>;
    }, [appContext, getMainRedirectUrl])

    return (
        <Switch>
            <NotAuthorizedRoute path='/login' redirect={<Redirect to={getMainRedirectUrl()}/>}>
                <LoginPage/>
            </NotAuthorizedRoute>

            <NotAuthorizedRoute path='/registration' redirect={<Redirect to={getMainRedirectUrl()}/>}>
                <RegistrationPage/>
            </NotAuthorizedRoute>

            <NotAuthorizedRoute path='/recovery-password' redirect={<Redirect to={getMainRedirectUrl()}/>}>
                <RecoveryPasswordPage/>
            </NotAuthorizedRoute>

            <CustomAuthorizedRoute path="/diary" requires={user} redirect={getRedirect()}>
                <DiaryPage/>
            </CustomAuthorizedRoute>

            <Redirect from="*" to={getMainRedirectUrl()}/>
        </Switch>
    )
}

export default RootRouter;
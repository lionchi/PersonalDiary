import React, {ReactElement} from "react";
import {Redirect, Route, Switch} from "react-router";
import LoginPage from "../pages/login/LoginPage";
import RegistrationPage from "../pages/registration/RegistrationPage";
import DiaryPage from "../pages/diary/DiaryPage";

const RootRouter = (): ReactElement => (
    <Switch>
        <Route exact path="/login" component={LoginPage}/>
        <Route exact path="/registration" component={RegistrationPage}/>
        <Route exact path="/diary" component={DiaryPage}/>

        <Redirect from="*" to="/login"/>
    </Switch>
)

export default RootRouter;
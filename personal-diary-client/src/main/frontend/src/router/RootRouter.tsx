import React, {ReactElement} from "react";
import {Redirect, Route, Switch} from "react-router";
import LoginPage from "../pages/login/LoginPage";

const RootRouter = (): ReactElement => (
    <Switch>
        <Route exact path="/login" component={LoginPage}/>

        <Redirect from="*" to="/login"/>
    </Switch>
)

export default RootRouter;
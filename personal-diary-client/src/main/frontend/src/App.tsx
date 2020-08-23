import React from 'react';
import {stores} from "./stores/stores";
import {BrowserRouter} from "react-router-dom";
import {Route} from "react-router";
import {baseUrl} from "./utils/config";
import RootRouter from "./router/RootRouter";
import {Provider} from "mobx-react";

function App() {
    return (
        <Provider {...stores}>
            <BrowserRouter basename={baseUrl()}>
                <Route component={RootRouter}/>
            </BrowserRouter>
        </Provider>
    );
}

export default App;

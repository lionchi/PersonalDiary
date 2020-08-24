import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import * as serviceWorker from './serviceWorker';
import App from "./App";
import 'antd/dist/antd.css';
import {ThemeSwitcherProvider} from "react-css-theme-switcher";
import {EConstantValueString} from "./common/EConstantValueString";

const themes = {
    dark: `${process.env.PUBLIC_URL}/dark-theme.css`,
    light: `${process.env.PUBLIC_URL}/light-theme.css`,
};

ReactDOM.render(
    <ThemeSwitcherProvider
        themeMap={themes}
        defaultTheme={localStorage.getItem(EConstantValueString.THEME) ? localStorage.getItem(EConstantValueString.THEME) as string : 'light'}>
        <App/>
    </ThemeSwitcherProvider>,
    document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();

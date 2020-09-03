import React, {ReactElement, ReactNode} from "react";
import {AuthorizedSection} from "@tshio/react-router-permissions";

interface ICustomAuthorizedSectionProps {
    requires: string[];
    children: ReactNode;
}

const CustomAuthorizedSection = (props: ICustomAuthorizedSectionProps): ReactElement => {

    return (
        <AuthorizedSection requires={props.requires}>
            {({isAuthorized}) => (isAuthorized ? props.children : null)}
        </AuthorizedSection>
    )
}

export default CustomAuthorizedSection;
import {createContext, useEffect, useState} from "react";

const UserContext = createContext({});

const getInitialState = () => {
    const currentUser = sessionStorage.getItem("currentUser");
    return currentUser ? JSON.parse(currentUser) : null
}

const UserProvider = ({children}) => {
    const [currentUser, setCurrentUser] = useState(getInitialState);

    useEffect(() => {
        sessionStorage.setItem("currentUser", JSON.stringify(currentUser))
    }, [currentUser])

    const userEnter = (user) => {
        setCurrentUser(user)
    }

    const userExit = () => {
        setCurrentUser(null)
    }

    return (
        <UserContext.Provider
            value={{currentUser, userEnter, userExit}}>
            { children }
        </UserContext.Provider>
    )
}

export { UserContext, UserProvider }
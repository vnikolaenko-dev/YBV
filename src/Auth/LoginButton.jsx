import {useNavigate} from 'react-router-dom'
function LoginButton(){
    const navigate = useNavigate();

    return(
        <button className="login-button" onClick={() => navigate("/auth")}>Become better</button>
    )
}
export default LoginButton;
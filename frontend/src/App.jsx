import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {UserProvider} from "./context/UserContext.jsx";
import Index from "./pages/Index.jsx";
import Main from "./pages/Main.jsx";
import AuthForm from "./Auth/AuthForm.jsx";


function App() {
  return (
      <UserProvider>
          <Router>
              <Routes>
                  <Route path="/" element={<Index/>}/>
                  <Route path="/auth" element={<AuthForm/>}/>
                  <Route path="/main" element={<Main/>}/>
              </Routes>
          </Router>
      </UserProvider>
  )
}

export default App

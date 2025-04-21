import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Index from "./pages/Index.jsx";
import Main from "./pages/Main.jsx";
import AuthForm from "././auth/AuthForm.jsx";


function App() {
  return (
      <Router>
          <Routes>
              <Route path="/" element={<Index/>}/>
              <Route path="/auth" element={<AuthForm/>}/>
              <Route path="/main" element={<Main/>}/>
          </Routes>
      </Router>
  )
}

export default App

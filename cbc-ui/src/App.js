import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import CreateItem from "./components/CreateItem";
import BulkUpload from "./components/BulkUpload";
import ItemViewer from "./components/ItemViewer";

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/create" element={<CreateItem />} />
        <Route path="/bulk" element={<BulkUpload />} />
        <Route path="/view" element={<ItemViewer />} />
      </Routes>
    </Router>
  );
};

export default App;

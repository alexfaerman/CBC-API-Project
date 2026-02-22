import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Layout from './components/Layout';
import HomePage from './components/HomePage';
import CreateItem from './components/CreateItem';
import BulkUpload from './components/BulkUpload';
import ItemViewer from './components/ItemViewer';
import NotFoundPage from './pages/NotFoundPage';

const App = () => {
  return (
    <Router>
      <Layout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/create" element={<CreateItem />} />
          <Route path="/bulk" element={<BulkUpload />} />
          <Route path="/view" element={<ItemViewer />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </Layout>
    </Router>
  );
};

export default App;

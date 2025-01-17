import React from "react";
import { Link } from "react-router-dom";

const HomePage = () => {
  return (
    <div>
      <h1>Welcome to CBC Item Manager</h1>
      <nav>
        <ul>
          <li><Link to="/create">Create Item</Link></li>
          <li><Link to="/bulk">Bulk Upload</Link></li>
          <li><Link to="/view">View Items</Link></li>
        </ul>
      </nav>
    </div>
  );
};

export default HomePage;

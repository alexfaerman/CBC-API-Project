import React from 'react';
import { NavLink } from 'react-router-dom';

const NAV_LINKS = [
  { to: '/', label: 'Home', end: true },
  { to: '/create', label: 'Create Item' },
  { to: '/bulk', label: 'Bulk Upload' },
  { to: '/view', label: 'View Item' },
];

const Layout = ({ children }) => {
  return (
    <div className="flex h-screen bg-gray-50 overflow-hidden">
      {/* Sidebar */}
      <aside className="w-60 shrink-0 bg-indigo-900 text-white flex flex-col">
        <div className="px-5 py-5 border-b border-indigo-800">
          <span className="text-lg font-bold tracking-tight">CBC Item Manager</span>
        </div>
        <nav className="flex-1 px-3 py-4 space-y-1">
          {NAV_LINKS.map(({ to, label, end }) => (
            <NavLink
              key={to}
              to={to}
              end={end}
              className={({ isActive }) =>
                `block px-3 py-2.5 rounded-lg text-sm font-medium transition-colors ${
                  isActive
                    ? 'bg-indigo-700 text-white'
                    : 'text-indigo-200 hover:bg-indigo-800 hover:text-white'
                }`
              }
            >
              {label}
            </NavLink>
          ))}
        </nav>
        <div className="px-5 py-4 border-t border-indigo-800 text-xs text-indigo-400">
          CBC API v1.0
        </div>
      </aside>

      {/* Main content */}
      <main className="flex-1 overflow-y-auto">
        <div className="max-w-3xl mx-auto px-8 py-8">
          {children}
        </div>
      </main>
    </div>
  );
};

export default Layout;

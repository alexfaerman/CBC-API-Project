import React from 'react';
import { Link } from 'react-router-dom';

const CARDS = [
  {
    to: '/create',
    title: 'Create Item',
    description: 'Add a single item from Content Source A, B, or C using the appropriate field schema.',
    badge: 'POST /items',
  },
  {
    to: '/bulk',
    title: 'Bulk Upload',
    description: 'Submit multiple items across all content sources in a single JSON payload.',
    badge: 'POST /items/bulk',
  },
  {
    to: '/view',
    title: 'View Item',
    description: 'Retrieve a saved item by its UUID and inspect all of its stored fields.',
    badge: 'GET /items/:id',
  },
];

const HomePage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
        <p className="mt-1 text-sm text-gray-500">
          Manage content items ingested from multiple upstream sources.
        </p>
      </div>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-3">
        {CARDS.map(({ to, title, description, badge }) => (
          <Link
            key={to}
            to={to}
            className="group flex flex-col justify-between p-5 bg-white rounded-xl border border-gray-200 shadow-sm hover:shadow-md hover:border-indigo-300 transition-all"
          >
            <div>
              <span className="inline-block text-xs font-mono bg-indigo-50 text-indigo-600 border border-indigo-100 rounded px-2 py-0.5 mb-3">
                {badge}
              </span>
              <h2 className="text-base font-semibold text-gray-900">{title}</h2>
              <p className="mt-1 text-sm text-gray-500 leading-relaxed">{description}</p>
            </div>
            <span className="mt-4 text-sm font-medium text-indigo-600 group-hover:underline">
              Open &rarr;
            </span>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default HomePage;

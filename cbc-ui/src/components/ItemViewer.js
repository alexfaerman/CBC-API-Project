import React, { useState } from 'react';
import { getItem } from '../services/apiService';

const FIELD_LABELS = [
  { key: 'id', label: 'Internal ID' },
  { key: 'externalId', label: 'External ID' },
  { key: 'title', label: 'Title' },
  { key: 'author', label: 'Author' },
  { key: 'publishedYear', label: 'Published Year' },
  { key: 'type', label: 'Type' },
];

const ItemViewer = () => {
  const [itemId, setItemId] = useState('');
  const [itemData, setItemData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleFetchItem = async () => {
    if (!itemId.trim()) return;
    setError(null);
    setItemData(null);
    setLoading(true);
    try {
      const data = await getItem(itemId.trim());
      setItemData(data);
    } catch (err) {
      setError('Item not found. Please check the UUID and try again.');
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') handleFetchItem();
  };

  return (
    <div>
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">View Item</h1>
        <p className="mt-1 text-sm text-gray-500">
          Enter a UUID to retrieve the stored item record.
        </p>
      </div>

      {/* Search */}
      <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-6 mb-4">
        <label className="block text-sm font-medium text-gray-700 mb-1">Item UUID</label>
        <div className="flex gap-2">
          <input
            type="text"
            value={itemId}
            onChange={(e) => setItemId(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder="e.g. 3fa85f64-5717-4562-b3fc-2c963f66afa6"
            className="flex-1 rounded-lg border border-gray-300 px-3 py-2 text-sm text-gray-900 placeholder-gray-400 font-mono focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition"
          />
          <button
            onClick={handleFetchItem}
            disabled={loading || !itemId.trim()}
            className="bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 text-white text-sm font-medium px-4 py-2 rounded-lg transition-colors whitespace-nowrap"
          >
            {loading ? 'Fetching…' : 'Fetch Item'}
          </button>
        </div>
      </div>

      {/* Error */}
      {error && (
        <div className="rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800">
          <span className="font-medium">Error:</span> {error}
        </div>
      )}

      {/* Result */}
      {itemData && (
        <div className="bg-white rounded-xl border border-gray-200 shadow-sm overflow-hidden">
          <div className="px-6 py-4 border-b border-gray-100">
            <h2 className="text-base font-semibold text-gray-900">Item Details</h2>
          </div>
          <dl className="divide-y divide-gray-100">
            {FIELD_LABELS.map(({ key, label }) => (
              <div key={key} className="flex items-start gap-4 px-6 py-3">
                <dt className="w-36 shrink-0 text-sm font-medium text-gray-500">{label}</dt>
                <dd className="flex-1 text-sm text-gray-900 font-mono break-all">
                  {itemData[key] ?? <span className="text-gray-400 italic">—</span>}
                </dd>
              </div>
            ))}
          </dl>
        </div>
      )}
    </div>
  );
};

export default ItemViewer;

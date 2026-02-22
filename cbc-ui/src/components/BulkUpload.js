import React, { useState } from 'react';
import { bulkUpload } from '../services/apiService';

const PLACEHOLDER = `{
  "ContentSourceA": {
    "itemId": "A001",
    "title": "Example Article",
    "author": "Jane Doe",
    "publishedYear": 2024,
    "typeA": "article"
  },
  "ContentSourceB": {
    "itemNumber": "B002",
    "itemTitle": "Example Story",
    "authorName": "John Smith",
    "yearPublished": 2023,
    "typeB": "story"
  }
}`;

const BulkUpload = () => {
  const [jsonData, setJsonData] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage('');
    setErrorMessage('');
    setLoading(true);

    try {
      const parsedData = JSON.parse(jsonData);

      const transformedData = Object.fromEntries(
        Object.entries(parsedData).map(([key, value]) => {
          const v = { ...value };
          if (v.publishedYear) v.publishedYear = parseInt(v.publishedYear, 10);
          if (v.yearPublished) v.yearPublished = parseInt(v.yearPublished, 10);
          if (v.year_of_publication) v.year_of_publication = parseInt(v.year_of_publication, 10);
          return [key, v];
        })
      );

      const items = await bulkUpload(transformedData);
      const count = Array.isArray(items) ? items.length : '?';
      setSuccessMessage(`Bulk upload successful — ${count} item(s) created.`);
      console.log('Bulk upload response:', items);
    } catch (error) {
      if (error instanceof SyntaxError) {
        setErrorMessage('Invalid JSON — please check your input and try again.');
      } else {
        setErrorMessage(
          error.response?.data?.message || 'An error occurred during bulk upload. Please try again.'
        );
      }
      console.error('Error in bulk upload:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Bulk Upload</h1>
        <p className="mt-1 text-sm text-gray-500">
          Paste a JSON object keyed by content source (e.g.{' '}
          <code className="text-indigo-600 bg-indigo-50 px-1 rounded">ContentSourceA</code>,{' '}
          <code className="text-indigo-600 bg-indigo-50 px-1 rounded">ContentSourceB</code>,{' '}
          <code className="text-indigo-600 bg-indigo-50 px-1 rounded">ContentSourceC</code>).
        </p>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-6">
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              JSON Payload
            </label>
            <textarea
              rows={14}
              value={jsonData}
              onChange={(e) => setJsonData(e.target.value)}
              placeholder={PLACEHOLDER}
              className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm font-mono text-gray-900 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition resize-y"
            />
          </div>

          <button
            type="submit"
            disabled={loading || !jsonData.trim()}
            className="w-full bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 text-white text-sm font-medium px-4 py-2.5 rounded-lg transition-colors"
          >
            {loading ? 'Uploading…' : 'Upload'}
          </button>
        </form>

        {successMessage && (
          <div className="mt-4 rounded-lg border border-green-200 bg-green-50 px-4 py-3 text-sm text-green-800">
            <span className="font-medium">Success:</span> {successMessage}
          </div>
        )}
        {errorMessage && (
          <div className="mt-4 rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800">
            <span className="font-medium">Error:</span> {errorMessage}
          </div>
        )}
      </div>
    </div>
  );
};

export default BulkUpload;

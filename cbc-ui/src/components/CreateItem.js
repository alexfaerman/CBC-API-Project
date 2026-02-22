import React, { useState } from 'react';
import { createItem } from '../services/apiService';

const SOURCE_FIELDS = {
  A: {
    externalIdKey: 'itemId',
    titleKey: 'title',
    authorKey: 'author',
    yearKey: 'publishedYear',
    typeKey: 'typeA',
    labels: { externalId: 'Item ID', title: 'Title', author: 'Author', year: 'Published Year', type: 'Type' },
  },
  B: {
    externalIdKey: 'itemNumber',
    titleKey: 'itemTitle',
    authorKey: 'authorName',
    yearKey: 'yearPublished',
    typeKey: 'typeB',
    labels: { externalId: 'Item Number', title: 'Item Title', author: 'Author Name', year: 'Year Published', type: 'Type' },
  },
  C: {
    externalIdKey: 'item_id',
    titleKey: 'name_of_work',
    authorKey: 'published_by',
    yearKey: 'year_of_publication',
    typeKey: 'typeC',
    labels: { externalId: 'Item ID', title: 'Name of Work', author: 'Published By', year: 'Year of Publication', type: 'Type' },
  },
};

const EMPTY_FORM = { externalId: '', title: '', author: '', year: '', type: '' };

const inputClass =
  'w-full rounded-lg border border-gray-300 px-3 py-2 text-sm text-gray-900 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 transition';
const labelClass = 'block text-sm font-medium text-gray-700 mb-1';

const CreateItem = () => {
  const [formValues, setFormValues] = useState(EMPTY_FORM);
  const [contentSource, setContentSource] = useState('A');
  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSourceChange = (e) => {
    setContentSource(e.target.value);
    setFormValues(EMPTY_FORM);
    setSuccessMessage('');
    setErrorMessage('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage('');
    setErrorMessage('');
    setLoading(true);
    const fields = SOURCE_FIELDS[contentSource];
    const payload = {
      [fields.externalIdKey]: formValues.externalId,
      [fields.titleKey]: formValues.title,
      [fields.authorKey]: formValues.author,
      [fields.yearKey]: parseInt(formValues.year, 10),
      [fields.typeKey]: formValues.type,
    };
    try {
      const item = await createItem(contentSource, payload);
      setSuccessMessage(`Item created successfully. Internal ID: ${item.id}`);
      setFormValues(EMPTY_FORM);
    } catch (error) {
      setErrorMessage('Failed to create item. Please check your input and try again.');
      console.error('Error creating item:', error);
    } finally {
      setLoading(false);
    }
  };

  const fields = SOURCE_FIELDS[contentSource];

  return (
    <div>
      <div className="mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Create Item</h1>
        <p className="mt-1 text-sm text-gray-500">
          Select a content source, then fill in the fields for that schema.
        </p>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm p-6">
        <form onSubmit={handleSubmit} className="space-y-5">
          {/* Source selector */}
          <div>
            <label htmlFor="contentSource" className={labelClass}>
              Content Source
            </label>
            <select
              id="contentSource"
              value={contentSource}
              onChange={handleSourceChange}
              className={inputClass}
            >
              <option value="A">Source A</option>
              <option value="B">Source B</option>
              <option value="C">Source C</option>
            </select>
          </div>

          {/* Dynamic fields */}
          {[
            { key: 'externalId', label: fields.labels.externalId, type: 'text' },
            { key: 'title', label: fields.labels.title, type: 'text' },
            { key: 'author', label: fields.labels.author, type: 'text' },
            { key: 'year', label: fields.labels.year, type: 'number' },
            { key: 'type', label: fields.labels.type, type: 'text' },
          ].map(({ key, label, type }) => (
            <div key={key}>
              <label className={labelClass}>{label}</label>
              <input
                type={type}
                value={formValues[key]}
                onChange={(e) => setFormValues({ ...formValues, [key]: e.target.value })}
                className={inputClass}
                placeholder={label}
                required
              />
            </div>
          ))}

          <button
            type="submit"
            disabled={loading}
            className="w-full bg-indigo-600 hover:bg-indigo-700 disabled:opacity-60 text-white text-sm font-medium px-4 py-2.5 rounded-lg transition-colors"
          >
            {loading ? 'Creating…' : 'Create Item'}
          </button>
        </form>

        {successMessage && (
          <div className="mt-4 flex items-start gap-2 rounded-lg border border-green-200 bg-green-50 px-4 py-3 text-sm text-green-800">
            <span className="font-medium">Success:</span> {successMessage}
          </div>
        )}
        {errorMessage && (
          <div className="mt-4 flex items-start gap-2 rounded-lg border border-red-200 bg-red-50 px-4 py-3 text-sm text-red-800">
            <span className="font-medium">Error:</span> {errorMessage}
          </div>
        )}
      </div>
    </div>
  );
};

export default CreateItem;

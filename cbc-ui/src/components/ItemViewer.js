import React, { useState } from 'react';
import { getItem } from '../services/apiService';

const ItemViewer = () => {
    const [itemId, setItemId] = useState('');
    const [itemData, setItemData] = useState(null); 
    const [error, setError] = useState(null);

    const handleFetchItem = async () => {
        try {
            setError(null); 
            const data = await getItem(itemId);
            setItemData(data); 
        } catch (err) {
            setItemData(null); 
            setError('Error fetching item. Please ensure the ID is correct.');
            console.error(err);
        }
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1>Item Viewer</h1>
            <div>
                <label htmlFor="itemId">Enter Item ID:</label>
                <input
                    id="itemId"
                    type="text"
                    value={itemId}
                    onChange={(e) => setItemId(e.target.value)}
                    placeholder="Enter UUID"
                    style={{ margin: '0 10px', padding: '5px', width: '300px' }}
                />
                <button onClick={handleFetchItem} style={{ padding: '5px 10px' }}>
                    Fetch Item
                </button>
            </div>
            <div style={{ marginTop: '20px' }}>
                {error && <div style={{ color: 'red' }}>{error}</div>}
                {itemData && (
                    <div>
                        <h2>Item Details:</h2>
                        <p><strong>ID:</strong> {itemData.id}</p>
                        <p><strong>External ID:</strong> {itemData.externalId}</p>
                        <p><strong>Title:</strong> {itemData.title}</p>
                        <p><strong>Author:</strong> {itemData.author}</p>
                        <p><strong>Published Year:</strong> {itemData.publishedYear}</p>
                        <p><strong>Type:</strong> {itemData.type}</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default ItemViewer;

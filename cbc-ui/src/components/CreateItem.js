import React, { useState } from 'react';
import { createItem } from '../services/apiService';
import '../styles/CreateItem.css';

const CreateItem = () => {
    const [itemData, setItemData] = useState({
        itemId: '',
        title: '',
        author: '',
        publishedYear: '',
        typeA: '',
    });
    const [contentSource, setContentSource] = useState('A');
    const [successMessage, setSuccessMessage] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // convert publishedYear to an integer
            const payload = {
                ...itemData,
                publishedYear: parseInt(itemData.publishedYear, 10),
            };
            const response = await createItem(contentSource, payload);
            setSuccessMessage("Item created successfully!");
            console.log('Item created successfully:', response);
        } catch (error) {
            console.error('Error creating item:', error);
            setSuccessMessage("Error creating item. Please try again.");
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="contentSource">Source Type:</label>
                    <select
                        id="contentSource"
                        value={contentSource}
                        onChange={(e) => setContentSource(e.target.value)}
                    >
                        <option value="">Select Source</option>
                        <option value="A">ContentSource A</option>
                        <option value="B">ContentSource B</option>
                        <option value="C">ContentSource C</option>
                    </select>
                </div>
                <div>
                    <label>
                        Item ID:
                        <input
                            type="text"
                            value={itemData.itemId}
                            onChange={(e) => setItemData({ ...itemData, itemId: e.target.value })}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Title:
                        <input
                            type="text"
                            value={itemData.title}
                            onChange={(e) => setItemData({ ...itemData, title: e.target.value })}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Author:
                        <input
                            type="text"
                            value={itemData.author}
                            onChange={(e) => setItemData({ ...itemData, author: e.target.value })}
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Published Year:
                        <input
                            type="number"
                            value={itemData.publishedYear}
                            onChange={(e) =>
                                setItemData({ ...itemData, publishedYear: e.target.value })
                            }
                        />
                    </label>
                </div>
                <div>
                    <label>
                        Type:
                        <input
                            type="text"
                            value={itemData.typeA}
                            onChange={(e) => setItemData({ ...itemData, typeA: e.target.value })}
                        />
                    </label>
                </div>
                <button type="submit">Create Item</button>
            </form>

            {successMessage && <p className="success-message">{successMessage}</p>}
        </div>
    );
};

export default CreateItem;

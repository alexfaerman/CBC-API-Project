import axios from 'axios';

export const bulkUpload = async (data) => {
    try {
        const response = await axios.post('/items/bulk', data, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error during bulk upload:', error);
        throw error;
    }
};

export const createItem = async (contentSource, itemData) => {
    try {
        const response = await axios.post(`/items?ContentSource=${encodeURIComponent(contentSource)}`, itemData, {
            headers: {
                'Content-Type': 'application/json',
            },
        });
        return response.data;
    } catch (error) {
        console.error('Error creating item:', error);
        throw error;
    }
};

export const getItem = async (id) => {
    try {
        const response = await axios.get(`/items/${id}`);
        return response.data;
    } catch (error) {
        console.error('Error fetching item:', error);
        throw error;
    }
};

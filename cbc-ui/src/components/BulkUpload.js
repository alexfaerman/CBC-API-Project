import React, { useState } from "react";
import { bulkUpload } from "../services/apiService";
import '../styles/BulkUpload.css';

const BulkUpload = () => {
  const [jsonData, setJsonData] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

 const handleSubmit = async (e) => {
    e.preventDefault();
    setSuccessMessage("");
    setErrorMessage("");

    try {
        // Parse the JSON data
        const parsedData = JSON.parse(jsonData);

        // Convert year fields to integers
        const transformedData = Object.fromEntries(
            Object.entries(parsedData).map(([key, value]) => {
                const updatedValue = { ...value };
                if (updatedValue.publishedYear) {
                    updatedValue.publishedYear = parseInt(updatedValue.publishedYear, 10);
                }
                if (updatedValue.yearPublished) {
                    updatedValue.yearPublished = parseInt(updatedValue.yearPublished, 10);
                }
                if (updatedValue.year_of_publication) {
                    updatedValue.year_of_publication = parseInt(updatedValue.year_of_publication, 10);
                }
                return [key, updatedValue];
            })
        );

        const response = await bulkUpload(transformedData);
        setSuccessMessage("Bulk upload successful!");
        console.log("Bulk upload response:", response.data);
    } catch (error) {
        setErrorMessage(
            error.response?.data?.message ||
            "An error occurred during bulk upload. Please check try again."
        );
        console.error("Error in bulk upload:", error);
    }
};


  return (
    <div className="bulk-upload-container">
      <h2>Bulk Upload</h2>
      <form onSubmit={handleSubmit}>
        <textarea
          rows="10"
          cols="50"
          value={jsonData}
          onChange={(e) => setJsonData(e.target.value)}
          placeholder="Enter JSON data here"
          className="bulk-upload-textarea"
        />
        <br />
        <button type="submit" className="bulk-upload-button">Upload</button>
      </form>

      {successMessage && <p className="success-message">{successMessage}</p>}
      {errorMessage && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default BulkUpload;


// 📁 components/ReviewStarInput.jsx
import React, { useState } from 'react';
import "../css/ReviewStarInput.css";

const ReviewStarInput = ({ rating, setRating }) => {
    const [hovered, setHovered] = useState(0);

    return (
        <div className="star-rating-input">
            {[1, 2, 3, 4, 5].map((star) => (
                <span
                    key={star}
                    className={`star ${star <= (hovered || rating) ? 'filled' : ''}`}
                    onClick={() => setRating(star)}
                    onMouseEnter={() => setHovered(star)}
                    onMouseLeave={() => setHovered(0)}
                >
                    ★
                </span>
            ))}
        </div>
    );
};

export default ReviewStarInput;

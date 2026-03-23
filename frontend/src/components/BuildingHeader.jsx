import { useState } from 'react';

function BuildingHeader({ building, onSetTemperature }) {
    const [tempInput, setTempInput] = useState(building.requestedTemperature);

    const handleSubmit = (e) => {
        e.preventDefault();
        const val = parseFloat(tempInput);
        if (!isNaN(val) && val >= 5 && val <= 45) {
            onSetTemperature(val);
        }
    };

    const heatingCount = building.rooms.filter(r => r.heatingEnabled).length;
    const coolingCount = building.rooms.filter(r => r.coolingEnabled).length;
    const idleCount = building.rooms.length - heatingCount - coolingCount;

    return (
        <div className="building-card">
            <div className="building-info">
                <div>
                    <div className="building-name">{building.name}</div>
                    <div className="room-detail" style={{ marginTop: 4 }}>
                        {building.rooms.length} room{building.rooms.length !== 1 ? 's' : ''} managed
                    </div>
                </div>
                <div className="building-stats">
                    <div className="stat">
                        <span className="stat-label">Setpoint</span>
                        <span className="stat-value">{building.requestedTemperature}°C</span>
                    </div>
                    <div className="stat">
                        <span className="stat-label">Heating</span>
                        <span className="stat-value" style={{ color: heatingCount > 0 ? '#f87171' : 'var(--text-muted)' }}>
                            {heatingCount}
                        </span>
                    </div>
                    <div className="stat">
                        <span className="stat-label">Cooling</span>
                        <span className="stat-value" style={{ color: coolingCount > 0 ? '#60a5fa' : 'var(--text-muted)' }}>
                            {coolingCount}
                        </span>
                    </div>
                    <div className="stat">
                        <span className="stat-label">Idle</span>
                        <span className="stat-value" style={{ color: '#34d399' }}>
                            {idleCount}
                        </span>
                    </div>
                </div>
            </div>

            <form className="temp-control" onSubmit={handleSubmit}>
                <label htmlFor="temp-input">Set Building Temperature:</label>
                <input
                    id="temp-input"
                    type="number"
                    step="0.5"
                    min="5"
                    max="45"
                    value={tempInput}
                    onChange={(e) => setTempInput(e.target.value)}
                />
                <button type="submit" className="btn btn-primary">Apply</button>
            </form>
        </div>
    );
}

export default BuildingHeader;

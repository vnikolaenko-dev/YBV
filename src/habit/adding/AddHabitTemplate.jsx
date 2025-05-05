import '../../styles/template.css';
export default function Templates({ onSelectTemplate }) {
    return (
        <div className="templates">
            <div className="preview-block" onClick={() => onSelectTemplate("smoking")}>
                <div className="smoking-preview" >
                    <div className="preview-icon">🚭</div>
                    <h3 className="preview-name">Quiet Smoking</h3>
                </div>
            </div>
        </div>
    );
    //
}

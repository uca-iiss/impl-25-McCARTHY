"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.ButtonComponent = void 0;
const Component_1 = require("./Component");
class ButtonComponent extends Component_1.Component {
    constructor(id, label) {
        super(id);
        this.label = label;
    }
    render() {
        return this.isVisible()
            ? `<button id="${this.id}">${this.label}</button>`
            : '';
    }
}
exports.ButtonComponent = ButtonComponent;

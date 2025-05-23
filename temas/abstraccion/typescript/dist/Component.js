"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Component = void 0;
class Component {
    constructor(id) {
        this.visible = true;
        this.id = id;
    }
    show() {
        this.visible = true;
    }
    hide() {
        this.visible = false;
    }
    isVisible() {
        return this.visible;
    }
}
exports.Component = Component;

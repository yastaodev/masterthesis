function sortWords(text) {
    const result = [];
    const words = text.split(' ');

    words.sort();

    for (const element of words) {
        result.push(element.replace(/[^a-zA-Z]/g, ""));
    }

    return result;
}

Polyglot.export('sortWords', sortWords);

import send from 'send';

export default function tabs(server) {
    // Setup home page
    server.get('/', (req, res, next) => {
        send(req, 'src/views/index.html').pipe(res);
    });

    // Setup the configure tab, with first and second as content tabs
    server.get('/configure', (req, res, next) => {
        send(req, 'src/views/index.html').pipe(res);
    });
}

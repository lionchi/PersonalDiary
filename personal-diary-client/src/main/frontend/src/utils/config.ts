export function baseUrl() {
    const applicationUrl = document.head.querySelector('meta[name=application-url]');
    const baseUrl = applicationUrl ? applicationUrl.getAttribute('content') : '/';
    return baseUrl == null ? '/' : baseUrl;
}
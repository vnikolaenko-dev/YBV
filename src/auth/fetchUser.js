
export async function authFetch(url, options = {}, requireAuth = true) {
    const token = sessionStorage.getItem("jwtToken");

    // Слияние заголовков
    const headers = {
        ...(options.headers || {}),
        ...(requireAuth && token ? { Authorization: `Bearer ${token}` } : {}),
    };

    const response = await fetch(url, {
        ...options,
        headers,
    });

    // Обработка отказа в доступе
    if (response.status === 401) {
        console.warn("Unauthorized or forbidden. Redirecting to login.");
        sessionStorage.removeItem("jwtToken");
        window.location.href = "/auth";
        return;
    }

    return response;

}

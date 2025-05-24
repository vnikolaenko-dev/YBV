
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

    if (response.status === 403) {
        console.warn("Forbidden: Access denied.");
        const text = await response.text();
        console.log("Forbidden response text:", text);
        try {
            const errorData = JSON.parse(text);
            console.error("Forbidden error details:", errorData);
        } catch (jsonError) {
            console.error("Failed to parse forbidden response:", jsonError);
        }
        return response;
    }

    return response;

}

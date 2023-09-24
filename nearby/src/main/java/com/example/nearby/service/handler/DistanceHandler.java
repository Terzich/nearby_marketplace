package com.example.nearby.service.handler;

import com.example.nearby.datamodel.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.*;

@Service
public class DistanceHandler {

    public List<Product> findNearestProducts(Double lat, Double lon, List<Product> productList) {
        return sortByNearest(productList, lat, lon);
    }

    private List<Product> sortByNearest(List<Product> products, Double lat, Double lon)
    {
        int n = products.size();
        for (int i = 1; i < n; ++i) {
            double key = calculateDistance(products.get(i).getGpsCoordinates(), lat, lon);
            Product product = products.get(i);
            int j = i - 1;

            while (j >= 0 && calculateDistance(products.get(j).getGpsCoordinates(), lat, lon) > key) {
                products.set(j + 1, products.get(j));
                j = j - 1;
            }
            products.set(j + 1, product);
        }
        return products;
    }

    private double calculateDistance(String coordinatesOfProduct, double lat1, double lon1) {
        String[] parts = coordinatesOfProduct.split("-");
        double lat2 = Double.parseDouble(parts[0]);
        double lon2 = Double.parseDouble(parts[1]);

        return haversine(lat1, lon1, lat2, lon2);
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1) * PI / 180.0;
        double dLon = (lon2 - lon1) * PI / 180.0;

        lat1 = (lat1) * PI / 180.0;
        lat2 = (lat2) * PI / 180.0;

        double a = pow(sin(dLat / 2), 2) + pow(sin(dLon / 2), 2) * cos(lat1) * cos(lat2);
        double rad = 6371;
        double c = 2 * asin(sqrt(a));
        return rad * c;
    }
}

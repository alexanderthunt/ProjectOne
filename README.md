# Broken Planetarium

# Break
This version of the application will have very long response time when finding a planet by name after that method has been called at least 5 times.

# Visual
## Broken
```java
int buffer_count = 0;
public Planet findByPlanetName(String name) {
        Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
        buffer_count++;
        if (buffer_count > 4) {
            try {
                Thread.sleep(5000, 0);
            } catch (InterruptedException e) {
            }
        }
        if (possiblePlanet.isPresent()) {
            return possiblePlanet.get();
        } else {
            throw new EntityNotFound("Planet not found");
        }
    }
```
## Fixed
```java
public Planet findByPlanetName(String name) {
        Optional<Planet> possiblePlanet = this.planetDao.findByPlanetName(name);
        if (possiblePlanet.isPresent()) {
            return possiblePlanet.get();
        } else {
            throw new EntityNotFound("Planet not found");
        }
    }
```
# Indicator
The indicator is very simple. The response time graphs in grafana will show response times over 200ms.
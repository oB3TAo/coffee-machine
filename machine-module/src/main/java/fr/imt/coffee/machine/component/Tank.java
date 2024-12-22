package fr.imt.coffee.machine.component;

public class Tank {
    private final double maxVolume;
    private final double minVolume;
    private double actualVolume;

    /**
     * Réservoir d'eau de la cafetière.
     * @param initialVolume Volume à mettre dans le réservoir à sa création
     * @param minVolume Volume minimal du réservoir
     * @param maxVolume Volume maximal du réservoir
     */
    public Tank(double initialVolume, double minVolume, double maxVolume) {
        if (minVolume > maxVolume) {
            throw new IllegalArgumentException("Minimum volume cannot be greater than maximum volume.");
        }
        if (initialVolume < minVolume || initialVolume > maxVolume) {
            throw new IllegalArgumentException("Initial volume must be within the range of min and max volumes.");
        }
        this.maxVolume = maxVolume;
        this.minVolume = minVolume;
        this.actualVolume = initialVolume;
    }

    /**
     * Réduit le volume de matière dans le réservoir
     * @param volumeToDecrease Volume de matière à enlever dans le réservoir
     */
    public void decreaseVolumeInTank(double volumeToDecrease) {
        if (volumeToDecrease < 0) {
            throw new IllegalArgumentException("Volume to decrease cannot be negative.");
        }
        if (this.actualVolume - volumeToDecrease < minVolume) {
            throw new IllegalArgumentException("Volume falls below minimum tank capacity.");
        }
        this.actualVolume -= volumeToDecrease;
    }

    /**
     * Augmente le volume de matière dans le réservoir
     * @param volumeToIncrease Volume de matière à ajouter dans le réservoir
     */
    public void increaseVolumeInTank(double volumeToIncrease) {
        if (volumeToIncrease < 0) {
            throw new IllegalArgumentException("Volume to increase cannot be negative.");
        }
        if (this.actualVolume + volumeToIncrease > maxVolume) {
            throw new IllegalArgumentException("Volume exceeds maximum tank capacity.");
        }
        this.actualVolume += volumeToIncrease;
    }

    /**
     * Vide complètement le réservoir.
     */
    public void emptyTank() {
        this.actualVolume = 0;
    }

    public boolean isEmpty() {
        return this.actualVolume == minVolume;
    }

    public boolean isFull() {
        return this.actualVolume == maxVolume;
    }

    public double getMaxVolume() {
        return maxVolume;
    }

    public double getMinVolume() {
        return minVolume;
    }

    public double getActualVolume() {
        return actualVolume;
    }
}

import java.util.Random;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;

public class Jugador {

    private int TOTAL_CARTAS = 10;
    private int MARGEN = 10;
    private int DISTANCIA = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];

    Random r = new Random();

    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int posicion = MARGEN + cartas.length * DISTANCIA;
        for (Carta c : cartas) {
            c.mostrar(pnl, posicion, MARGEN);
            posicion -= DISTANCIA;
        }
        pnl.repaint();
    }

    public String getGrupos() {
        String mensaje = "No se encontraron grupos";

        int[] contadores = new int[NombreCarta.values().length];
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int c : contadores) {
            if (c >= 2) {
                hayGrupos = true;
                break;
            }
        }

        if (hayGrupos) {
            mensaje = "Se encontraron los siguientes grupos:\n";
            int p = 0;
            for (int c : contadores) {
                if (c >= 2) {
                    mensaje += Grupo.values()[c] + " de " + NombreCarta.values()[p] + "\n";
                }
                p++;
            }
}
        // Buscar escaleras de la misma pinta

List<Carta> listaCartas = Arrays.asList(cartas);
List<NombreCarta> secuencia = new ArrayList<>();
listaCartas.sort(Comparator.comparing(Carta::getPinta).thenComparing(Carta::getNombre));

    Pinta pintaActual = null;
    String mensajeEscaleras = "";

    for (Carta carta : listaCartas) {
        if (pintaActual == null || carta.getPinta() != pintaActual) {
        if (secuencia.size() >= 3) {
            mensajeEscaleras += "Escalera de " + pintaActual + ": " + secuencia + "\n";
        }
        pintaActual = carta.getPinta();
        secuencia.clear();
    }
    if (secuencia.isEmpty() || carta.getNombre().ordinal() == secuencia.get(secuencia.size() - 1).ordinal() + 1) {
        secuencia.add(carta.getNombre());
    } else {
        if (secuencia.size() >= 3) {
            mensajeEscaleras += "Escalera de " + pintaActual + ": " + secuencia + "\n";
        }
        secuencia.clear();
        secuencia.add(carta.getNombre());
    }
    }
    if (secuencia.size() >= 3) {
    mensajeEscaleras += "Escalera de " + pintaActual + ": " + secuencia + "\n";
    }
    if (!mensajeEscaleras.isEmpty()) {
    mensaje += "\nEscaleras encontradas:\n" + mensajeEscaleras;
    }else {
    mensaje += "\nNo se encontraron escaleras";
    }
    return mensaje;
    }

    public int getPuntajeCartasNoEnEscalera() {
        List<Carta> listaCartas = Arrays.asList(cartas);
        List<NombreCarta> secuencia = new ArrayList<>();
        listaCartas.sort(Comparator.comparing(Carta::getPinta).thenComparing(Carta::getNombre));
    
        Pinta pintaActual = null;
        List<Carta> cartasEnEscalera = new ArrayList<>();
    
        for (Carta carta : listaCartas) {
            if (pintaActual == null || carta.getPinta() != pintaActual) {
                if (secuencia.size() >= 3) {
                    cartasEnEscalera.addAll(listaCartas.subList(listaCartas.indexOf(carta) - secuencia.size(), listaCartas.indexOf(carta)));
                }
                pintaActual = carta.getPinta();
                secuencia.clear();
            }
    
            if (secuencia.isEmpty() || carta.getNombre().ordinal() == secuencia.get(secuencia.size() - 1).ordinal() + 1) {
                secuencia.add(carta.getNombre());
            } else {
                if (secuencia.size() >= 3) {
                    cartasEnEscalera.addAll(listaCartas.subList(listaCartas.indexOf(carta) - secuencia.size(), listaCartas.indexOf(carta)));
                }
                secuencia.clear();
                secuencia.add(carta.getNombre());
            }
        }
    
        if (secuencia.size() >= 3) {
            cartasEnEscalera.addAll(listaCartas.subList(listaCartas.size() - secuencia.size(), listaCartas.size()));
        }
    
        // Calcular el puntaje de las cartas no incluidas en las ecsaleras.
        int suma = 0;
        for (Carta c : cartas) {
            if (!cartasEnEscalera.contains(c)) {
                suma += c.getValor();
            }
        }
        
        return suma;
    }

}
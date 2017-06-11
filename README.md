# SimulationPS
Proseminar Simulation SS 2017 at the University of Salzburg(Computer-Science)
---
Topic 7: Comparison of Different Waiting Schemes

![alt text](https://github.com/Sykona/SimulationPS/blob/master/Sim7/presentation/images/scenario1.png)

Vergleich von Warte-Strategien

Es sind mittels Simulation folgende Strategien zur Organisation von wartenden Kunden zu vergleichen. In einem Bahnhof gibt es zwei Schalter für Fahrkarten.
Ist es nun günstiger die wartenden Kunden in eine Warteschlange zu geben und wenn ein Schalter frei ist, wird die am längsten wartenden Kundschaft bedient.
Oder ist es besser, wenn jeder Schalter eine eigene Warteschlange besitzt. Im zweiten Fall muss die Kundschaft schon bei Ankunft am Bahnhof sich entscheiden,
bei welchem Schalter sie warten will.

Wie sieht es aus, wenn mehr als zwei Schalter vorhanden sind? Bei der zweiten Variante kann auch überlegt werden, nach welchen Kriterien von ankommenden Kunden
die Warteschlange ausgewählt wird oder auch einen eventuellen Warteschlangenwechsel beachten (wenn eine andere Warteschlange kürzer wird).

Vergleichen Sie hier mehrere Strategien.

For our Solution we used Java 8 with the following Libraries:

  + DESMO-J http://desmoj.sourceforge.net/home.html
  + JFreeChart http://www.jfree.org/jfreechart/
  
You can find the complete project report in our presentation file. https://github.com/Sykona/SimulationPS/blob/master/Sim7/presentation/sim7.pdf

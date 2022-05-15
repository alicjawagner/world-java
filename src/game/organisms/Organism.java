package game.organisms;

import game.world.World;
import java.awt.*;

public abstract class Organism {
    protected String name;
    protected int strength;
    protected int initiative;
    protected Point point;
    protected int birthTime;
    protected boolean isAlive;
    protected int stepRange;
    protected World world;

    Organism(World world) {
        world = world;
        strength = -1;
        initiative = -1;
        isAlive = true;
        stepRange = 1;
        birthTime = world.getNumberOfBornOrganisms() + 1;

        int x, y;
        int wys = swiat.getWysokosc();
        int szer = swiat.getSzerokosc();
        do {
            x = rand() % wys;
            y = rand() % szer;
        } while (swiat.plansza[x][y] != SYMBOL_PUSTEGO_POLA);

        polozenie.x = x;
        polozenie.y = y;

    }

    protected enum Strength {
        STRONGER,
        EQUAL,
        WEAKER
    }

    protected Strength amIStronger(final Organism other) {
        if (strength > other.strength) return Strength.STRONGER;
	else if (strength == other.strength) return Strength.EQUAL;
	else return Strength.WEAKER;
    }
}

/*
* class Organizm {
protected:
	virtual int kimJestem() const = 0;
public:
	Organizm(Swiat& swiat);
	~Organizm();

	std::string getNazwa() const;
	int getSila() const;
	int getInicjatywa() const;
	Polozenie getPolozenie() const;
	int getCzasNarodzin() const;
	bool getCzyZyje() const;
	Swiat& getSwiat() const;

	void setSila(const int& ile);
	void setCzasNarodzin(const int& ile);

	virtual std::vector<Polozenie> znajdzPolaDoRuchu() const;
	void usunZajetePola(std::vector<Polozenie>& pola);
	void przesunNaPole(Polozenie& pole);
	virtual bool czyOdbilemAtak(const Organizm& atakujacy) const;
	virtual bool czyUcieklem(Zwierze& atakujacy);
	virtual bool czyWygralemWalke(Organizm& atakujacy);
	virtual void piszWygrywam() const = 0;
	virtual void piszUmieram() const;
	void umrzyj();
	virtual void akcja() = 0;
	virtual char rysujNaPlanszy() = 0;
	void stworzDziecko(std::vector<Polozenie>& pola) const;
};
*
*
* Organizm::Organizm(Swiat& swiat) : swiat(swiat) {
	sila = -1;
	inicjatywa = -1;
	czyZyje = true;
	zasiegRuchu = 1;
	czasNarodzin = swiat.getLiczbaNarodzonychZwierzat() + 1;

	int x, y;
	int wys = swiat.getWysokosc();
	int szer = swiat.getSzerokosc();
	do {
		x = rand() % wys;
		y = rand() % szer;
	} while (swiat.plansza[x][y] != SYMBOL_PUSTEGO_POLA);

	polozenie.x = x;
	polozenie.y = y;

}

Organizm::~Organizm() {
}



//gettery
std::string Organizm::getNazwa() const {
	return nazwa;
}

int Organizm::getSila() const {
	return sila;
}

int Organizm::getInicjatywa() const {
	return inicjatywa;
}

Polozenie Organizm::getPolozenie() const {
	return polozenie;
}

int Organizm::getCzasNarodzin() const {
	return czasNarodzin;
}

bool Organizm::getCzyZyje() const {
	return czyZyje;
}

Swiat& Organizm::getSwiat() const {
	return swiat;
}


void Organizm::setSila(const int& ile) {
	sila = ile;
}

void Organizm::setCzasNarodzin(const int& ile) {
	czasNarodzin = ile;
}



std::vector<Polozenie> Organizm::znajdzPolaDoRuchu() const {
	Polozenie obecny, doDodania;
	obecny = polozenie;

	std::vector<Polozenie> polaDoRuchu;

	for (int i = -1 * zasiegRuchu; i <= zasiegRuchu; i += zasiegRuchu) {
		for (int j = -1 * zasiegRuchu; j <= zasiegRuchu; j += zasiegRuchu) {
			if (i == 0 && j == 0)
				continue;

			doDodania = { obecny.x + i, obecny.y + j };
			if (swiat.czyPoleNalezyDoPlanszy(doDodania))
				polaDoRuchu.push_back(doDodania);
		}
	}

	return polaDoRuchu;
}



void Organizm::usunZajetePola(std::vector<Polozenie>& pola) {
	if (pola.size() <= 0)
		return;

	std::vector<int> doUsuniecia;

	for (int p = 0; p < pola.size(); p++) {
		if (swiat.czyPoleWolne(pola[p]) == false)
			doUsuniecia.push_back(p);
	}

	for (int i = doUsuniecia.size() - 1; i >= 0; i--) {
		int p = doUsuniecia[i];
		pola.erase(pola.begin() + p);
	}
}



void Organizm::przesunNaPole(Polozenie& pole) {
	swiat.zwolnijPoleNaPlanszy(polozenie);
	swiat.plansza[pole.x][pole.y] = rysujNaPlanszy();
	polozenie = pole;
}



bool Organizm::czyOdbilemAtak(const Organizm& atakujacy) const {
	return false;
}



bool Organizm::czyUcieklem(Zwierze& atakujacy) {
	return false;
}



int Organizm::czyJestemSilniejszy(const Organizm& drugi) const {
	if (this->sila > drugi.sila) return SILNIEJSZY;
	else if (this->sila == drugi.sila) return ROWNY;
	else return SLABSZY;
}



bool Organizm::czyWygralemWalke(Organizm& atakujacy) {
	if (czyJestemSilniejszy(atakujacy) == SILNIEJSZY)
		return true;
	return false;
}



void Organizm::piszUmieram() const {
	std::cout << nazwa << " nie zyje :(" << std::endl;
}



void Organizm::umrzyj() {
	czyZyje = false;
	swiat.zwolnijPoleNaPlanszy(this->polozenie);
	piszUmieram();
}



void Organizm::stworzDziecko(std::vector<Polozenie>& pola) const {
	Organizm* dziecko = swiat.stworzOrganizm(this->kimJestem());

	int ktore = rand() % pola.size();
	dziecko->przesunNaPole(pola[ktore]);
	swiat.dodajDoVectoraDoDodania(dziecko);
}
* */
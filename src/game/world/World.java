package game.world;

import game.organisms.Organism;

import java.awt.*;
import java.util.ArrayList;

public class World {
    public static final int BOARD_SIZE = 700;
    public static final int FIELD_SIZE = 35;
    public static final int FIELDS_NUMBER = (BOARD_SIZE / FIELD_SIZE); //20 - how many fields each dimension
    public Organism[][] board = new Organism[FIELDS_NUMBER][FIELDS_NUMBER];
    private int numberOfBornOrganisms = 0;
    private boolean isHumanAlive = true;
    //private Human human;
    public ArrayList<Organism> organisms;
    public ArrayList<Organism> toAdd;

    public World() {
        initializeBoard();
    }

    private void initializeBoard() {
        for(Organism[] rowOfBoard : board) {
            for(Organism o : rowOfBoard) {
                o = null;
            }
        }
    }

    public int getNumberOfBornOrganisms() {
        return numberOfBornOrganisms;
    }

    public boolean isFieldInBoard(final Point point) {
        return point.x >= 0 && point.x < FIELDS_NUMBER && point.y >= 0 && point.y < FIELDS_NUMBER;
    }

    public boolean isFieldUnoccupied(final Point point) {
        return board[point.x][point.y] == null;
    }

    public void clearTheField(final Point point) {
        board[point.x][point.y] = null;
    }

    public Organism searchArrayList(final Point point, final ArrayList<Organism> arr) {
        for (Organism o : arr) {
            if (o.getIsAlive() && o.point == point) {
                return o;
            }
        }
        return null;
    }

    public Organism findOnField(final Point point) {
        Organism org = searchArrayList(point, organisms);
        if (org != null)
            return org;

        org = searchArrayList(point, toAdd);
        return org;
    }

    private void removeDead() {
        organisms.removeIf(o -> (!o.getIsAlive()));
        toAdd.removeIf(o -> (!o.getIsAlive()));
    }

    /*
    skastuj na plant albo animal

    const int Swiat::coJestNaPlanszy(const Polozenie& miejsce) const {
        char pole = plansza[miejsce.x][miejsce.y];

        switch (pole) {
            case SYMBOL_PUSTEGO_POLA:
                return PUSTE_POLE;
            case SYMBOL_LISA:
                return LIS;
            case SYMBOL_WILKA:
                return WILK;
            case SYMBOL_OWCY:
                return OWCA;
            case SYMBOL_ANTYLOPY:
                return ANTYLOPA;
            case SYMBOL_CZLOWIEKA:
                return CZLOWIEK;
            case SYMBOL_ZOLWIA:
                return ZOLW;
            case SYMBOL_TRAWY:
                return TRAWA;
            case SYMBOL_MLECZA:
                return MLECZ;
            case SYMBOL_GUARANY:
                return GUARANA;
            case SYMBOL_JAGOD:
                return JAGODY;
            case SYMBOL_BARSZCZU:
                return BARSZCZ;
        }

        return -1;
    }

    Czlowiek* Swiat::znajdzCzlowieka() const {
        Czlowiek* szukany = nullptr;
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy[i]->getNazwa() == "czlowiek") {
                szukany = dynamic_cast<Czlowiek*>(organizmy[i]);
                return szukany;
            }
        }
	    return szukany;
    }
     */
}

/*
class Swiat {
private:

	void rysujPlansze() const;

	void usunMartwe();
	void stworzIDodajOrganizm(const int& jaki);
	//zwraca indeks organimzu, przed ktorym ma byc wstawiony nowy; jesli na koncu: -1
	int znajdzMiejsceWVectorzeOrganizmy(const Organizm& doWstawienia) const;
	int wykonajTure();

public:
	void zwolnijPoleNaPlanszy(const Polozenie& miejsce);
	const int coJestNaPlanszy(const Polozenie& miejsce) const;
	Organizm* przeszukajVector(const Polozenie& miejsce, const std::vector<Organizm*>& vect) const;
	Organizm* znajdzOrganizmNaPolu(const Polozenie& miejsce) const;
	Organizm* stworzOrganizm(const int& jaki);
	void dodajOrganizm(Organizm*& doDodania);
	void dodajDoVectoraDoDodania(Organizm*& doDodania);

	void przygotujDoGry();
	void rozpocznijGre();

void Swiat::rysujPlansze() const {
	for (int i = 0; i < wysokosc; i++) {
		for (int j = 0; j < szerokosc; j++)
			std::cout << plansza[i][j];
		std::cout << std::endl;
	}
}









Organizm* Swiat::stworzOrganizm(const int& jaki) {
	Organizm* o = nullptr;
	switch (jaki) {
	case LIS:
		o = new Lis(*this);
		break;
	case WILK:
		o = new Wilk(*this);
		break;
	case OWCA:
		o = new Owca(*this);
		break;
	case ANTYLOPA:
		o = new Antylopa(*this);
		break;
	case CZLOWIEK:
		o = new Czlowiek(*this);
		break;
	case ZOLW:
		o = new Zolw(*this);
		break;
	case TRAWA:
		o = new Trawa(*this);
		break;
	case MLECZ:
		o = new Mlecz(*this);
		break;
	case GUARANA:
		o = new Guarana(*this);
		break;
	case JAGODY:
		o = new WilczeJagody(*this);
		break;
	case BARSZCZ:
		o = new BarszczSosnowskiego(*this);
		break;
	}
	return o;
}



void Swiat::dodajOrganizm(Organizm*& doDodania) {
	int przedTym = znajdzMiejsceWVectorzeOrganizmy(*doDodania);
	if (przedTym == -1)
		organizmy.push_back(doDodania);
	else
		organizmy.insert(organizmy.begin() + przedTym, doDodania);

	plansza[doDodania->getPolozenie().x][doDodania->getPolozenie().y] = doDodania->rysujNaPlanszy();
	liczbaNarodzonychOrganizmow++;
}



void Swiat::dodajDoVectoraDoDodania(Organizm*& doDodania) {
	orgDoDodania.push_back(doDodania);
}



void Swiat::stworzIDodajOrganizm(const int& jaki) {
	Organizm* o = stworzOrganizm(jaki);
	dodajOrganizm(o);
}



int Swiat::znajdzMiejsceWVectorzeOrganizmy(const Organizm& doWstawienia) const {
	int przedTym = -1;
	for (int i = 0; i < organizmy.size(); i++) {
		if (organizmy[i]->getInicjatywa() < doWstawienia.getInicjatywa()) {
			przedTym = i;
			return przedTym;
		}
	}
	return przedTym;
}



int Swiat::wykonajTure() {
	Czlowiek* czlowiek = znajdzCzlowieka();
	int klawisz = czlowiek->wczytajStrzalki();

	system("cls");

	if (klawisz == ESC)
		return ESC;
	if (klawisz == ELIKSIR)
		czlowiek->wlaczEliksir();

	for (Organizm*& o : organizmy) {
		if (o->getCzyZyje() == true)
			o->akcja();
	}

	usunMartwe();

	for (Organizm*& o : orgDoDodania) {	//dodaj oczekujace organizmy do wlasciwego vectora
		dodajOrganizm(o);
		o->setCzasNarodzin(liczbaNarodzonychOrganizmow);
	}
	orgDoDodania.clear();

	rysujPlansze();
	std::cout << std::endl;

	return 1;
}



void Swiat::przygotujDoGry() {
	for (int i = 0; i < LICZBA_GATUNKOW; i++) {
		for (int j = 0; j < POCZ_LICZBA_ORGANIZMOW_GATUNKU; j++) {
			if (i == CZLOWIEK) {
				stworzIDodajOrganizm(CZLOWIEK);
				break;
			}
			stworzIDodajOrganizm(i);
		}
	}

	rysujPlansze();
}



void Swiat::rozpocznijGre() {
	int ch = 1;

	std::cout << "Rozpoczynamy gre!" << std::endl;
	while (ch != ESC) {
		std::cout << "Nowa runda: strzalka. Eliksir (+5 do Twojej sily): E. Wyjscie: esc." << std::endl << std::endl;
		ch = wykonajTure();
		if (znajdzCzlowieka() == nullptr) {
			std::cout << "Zostales pokonany, koniec gry :C. Powodzenia nastepnym razem!" << std::endl;
			break;
		}
	}
}



Swiat::~Swiat() {
	for (Organizm*& o : organizmy) {
		if (o != nullptr)
			delete o;
	}

	zwolnijPamiecPlanszy();
}
};

 */
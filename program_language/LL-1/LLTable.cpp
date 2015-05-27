#include "LLTable.h"

void print_LLTable(){
	
	//넌터미널과 터미널 셋 설정
	set_terset();
	set_nontset();

	puts("LL Table : ");
	puts("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	cout << "+\t+";
	
	for (set<string>::iterator itr = nontset.begin(); itr != nontset.end(); itr++){
		cout.width(8);
		cout << *itr ;
		cout << "       + ";
	}
	cout << endl;
	puts("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

	for (set<string>::iterator titr = terset.begin(); titr != terset.end(); titr++){
		cout << "+ ";
		cout.width(3);
		cout << *titr << "   + ";

		set_vgrm(*titr);
		vector<string> ntvgrm(vgrammar.begin(),vgrammar.end());
		vgrm_ntsetting(ntvgrm);

		sort(ntvgrm.begin(), ntvgrm.end());
		sort_ntvgrm(ntvgrm);
		vector<string>::iterator vgitr = ntvgrm.begin();


		for (set<string>::iterator nitr = nontset.begin(); nitr != nontset.end(); nitr++){
			int decision;
			if (is_ter(vgitr->at(0)))
				decision = (string(1, vgitr->at(2)).compare(*nitr));
			else
				decision = (string(1, vgitr->at(0)).compare(*nitr));

			if (decision == 0){
				
				cout << *titr << " -> "<<*vgitr;
				cout.width(8);
				cout << "    + ";
				vgitr++;
			}
			else {
				cout.width(16);
				cout << "+ ";
			}
		}

		cout << endl;
	}
	puts("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

}
void vgrm_ntsetting(vector<string>& ntvgrm){
	int size = ntvgrm.size();
	for (int i = 0; i<size ; i++){
		if (ntvgrm[i].at(0) >= 'A' && ntvgrm[i].at(0) <= 'Z' &&ntvgrm[i].at(1)!='('){
			set_vfirst(string(1, ntvgrm[i].at(0)));
			for (int j = 0; j < vfirst.size(); j++){
				string str = ntvgrm[i].substr(0, 1) + "(" + vfirst[j] + ")" + ntvgrm[i].substr(1, ntvgrm[i].size());
				ntvgrm.push_back(str);
				
			}
			ntvgrm.erase(ntvgrm.begin() + i);
		}
	}
}

//삽입정렬로 원하는 대로 정렬하게 함
void sort_ntvgrm(vector<string>& ntvgrm){
	for (int i = 1; i<ntvgrm.size(); i++){
		string temp = ntvgrm[i];
		int	j = i - 1;
		while (j >= 0 && sort_decision(ntvgrm[j] ,temp)>0){
			ntvgrm[j + 1] = ntvgrm[j];
			j = j - 1;
		}
		ntvgrm[j + 1] = temp;
	}
}

int sort_decision(string str1, string str2){
	if (is_ter(str1.at(0))){
		if (is_ter(str2.at(0)))
			return str1.substr(2,str1.size()).compare(str2);
		return str1.substr(2, str1.size()).compare(str2.substr(2, str2.size()));
	}
	return  str1.compare(str2);
}

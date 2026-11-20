
package com.example.mobileship.data.repository

import com.example.mobileship.data.local.dao.CityInfoDao
import com.example.mobileship.data.local.entity.CityInfoEntity
import com.example.mobileship.domain.model.CityInfo
import com.example.mobileship.domain.model.DomainResult
import com.example.mobileship.domain.repository.CityInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class CityInfoRepositoryImpl @Inject constructor(
    private val dao: CityInfoDao
) : CityInfoRepository {

    override fun getCities(): Flow<List<CityInfo>> {
        return dao.getCities()
            .onStart {
                if (dao.getCities().first().isEmpty()) {
                    dao.insertCities(
                        listOf(
                            CityInfoEntity(
                                idCity = "1",
                                name = "Нижний Новгород",
                                population = 1205000,
                                yearFoundation = 1221,
                                description = "Нижний Новгород расположен в месте слияния Волги и Оки. Город был основан в 1221 году князем Юрием Всеволодовичем. Со временем он стал крупным торговым и культурным центром России. Среди главных достопримечательностей города — Нижегородский кремль, Чкаловская лестница и Большая Покровская улица. С высокого берега открываются красивые виды на две великие реки.",
                                imageUrl = "nizhnynovgorod"
                            ),
                            CityInfoEntity(
                                idCity = "2",
                                name = "Кострома",
                                population = 256000,
                                yearFoundation = 1152,
                                description = "Кострома — старинный город на берегу Волги, входящий в Золотое кольцо России. Он известен купеческой архитектурой, историей династии Романовых и традициями ювелирного мастерства. Среди интересных мест — Ипатьевский монастырь, Торговые ряды и пожарная каланча. Город сохраняет атмосферу старинного русского поселения.",
                                imageUrl = "kostroma"
                            ),
                            CityInfoEntity(
                                idCity = "3",
                                name = "Ярославль",
                                population = 567000,
                                yearFoundation = 1010,
                                description = "Ярославль — древний город на берегу Волги. Его исторический центр включён в список Всемирного наследия ЮНЕСКО. Здесь находятся старинные храмы, монастыри, музеи и живописные набережные. Особое место занимает Спасо-Преображенский монастырь — один из важнейших исторических памятников города.",
                                imageUrl = "yaroslavl"
                            ),
                            CityInfoEntity(
                                idCity = "4",
                                name = "Углич",
                                population = 316000,
                                yearFoundation = 937,
                                description = "Углич — старинный город на берегу Волги с богатой историей. Он особенно известен событиями, связанными с царевичем Дмитрием. В центре города расположен Угличский кремль, где можно увидеть церковь Димитрия на Крови и Спасо-Преображенский собор. Город привлекает путешественников старинной архитектурой и красивыми речными пейзажами.",
                                imageUrl = "uglich"
                            ),
                            CityInfoEntity(
                                idCity = "5",
                                name = "Москва",
                                population = 13150000,
                                yearFoundation = 1147,
                                description = "Москва — столица России и один из крупнейших культурных, исторических и деловых центров страны. Первое летописное упоминание города относится к 1147 году. Главные символы столицы — Кремль, Красная площадь и собор Василия Блаженного. Здесь находятся многочисленные музеи, театры, парки, исторические усадьбы и современные архитектурные комплексы.",
                                imageUrl = "moscow"
                            ),
                            CityInfoEntity(
                                idCity = "6",
                                name = "Константиново",
                                population = 3000,
                                yearFoundation = 1619,
                                description = "Константиново — живописное село в Рязанской области, расположенное на высоком берегу Оки. Оно известно как родина поэта Сергея Есенина. Здесь находится музей-заповедник, включающий родительский дом поэта, земскую школу и другие исторические постройки. С высокого берега открываются красивые виды на Оку и окружающие просторы.",
                                imageUrl = "konstantinovo"
                            ),
                            CityInfoEntity(
                                idCity = "7",
                                name = "Рязань",
                                population = 520500,
                                yearFoundation = 1095,
                                description = "Рязань — исторический город с богатым культурным наследием. Его главная достопримечательность — Рязанский кремль, архитектурный ансамбль с соборами и старинными зданиями. Город связан с историей древнего Рязанского княжества, а также с именами известных писателей и учёных. Здесь можно посетить музеи и познакомиться с традициями Рязанского края.",
                                imageUrl = "ryazan"
                            ),
                            CityInfoEntity(
                                idCity = "8",
                                name = "Касимов",
                                population = 27500,
                                yearFoundation = 1152,
                                description = "Касимов расположен на берегу Оки. Его история интересна переплетением русской и татарской культур. В городе сохранились старинная мечеть и минарет, православные храмы, купеческие дома и исторические площади. Касимов отличается необычным архитектурным обликом и атмосферой старинного города.",
                                imageUrl = "kasimov"
                            ),
                            CityInfoEntity(
                                idCity = "9",
                                name = "Муром",
                                population = 104000,
                                yearFoundation = 862,
                                description = "Муром — один из древнейших городов России, расположенный на берегу Оки. Он известен былинным богатырём Ильёй Муромцем и православными монастырями. Среди главных достопримечательностей — Спасо-Преображенский и Троицкий монастыри, а также памятник Петру и Февронии. Город славится историческими храмами и красивыми видами на реку.",
                                imageUrl = "murom"
                            ),
                            CityInfoEntity(
                                idCity = "10",
                                name = "Павлово",
                                population = 55900,
                                yearFoundation = 1566,
                                description = "Павлово — город на берегу Оки, известный своими ремесленными традициями. Особую известность ему принесли павловские ножи, замки и изделия местных мастеров. Город также знаменит традицией выращивания лимонов в домашних условиях. Здесь можно познакомиться с историей местных промыслов, посетить краеведческий музей и прогуляться по старым улицам.",
                                imageUrl = "pavlovo"
                            )
                        )
                    )
                }
            }
            .map { entities ->
                entities.map { entity ->
                    CityInfo(
                        id = entity.idCity,
                        name = entity.name,
                        population = entity.population,
                        yearFoundation = entity.yearFoundation,
                        description = entity.description,
                        imageUrl = entity.imageUrl
                    )
                }
            }
    }

    override suspend fun getDetailsCities(
        cityId: String
    ): DomainResult<CityInfo> {
        TODO("Реализация зависит от структуры DomainResult")
    }
}

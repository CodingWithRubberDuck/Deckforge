DROP TABLE IF EXISTS user_participate_event;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS owned_card;
DROP TABLE IF EXISTS deck_contain_card;
DROP TABLE IF EXISTS deck;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS card_list;


CREATE TABLE user (
                      user_id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      email VARCHAR(100) NOT NULL UNIQUE,
                      password_hash VARCHAR(400) NOT NULL,
                      role ENUM ('USER', 'ORGANIZER', 'ADMIN') DEFAULT ('USER'),
                      last_logged_in DATE,
                      date_asked_for_removal DATE,
                      profile_visibility ENUM ('PRIVATE', 'PUBLIC')
);

CREATE TABLE event (
                       event_id INT AUTO_INCREMENT PRIMARY KEY,
                       owner_id INT,
                       FOREIGN KEY (owner_id) REFERENCES user(user_id) ON DELETE CASCADE,
                       max_slots INT NOT NULL,
                       location VARCHAR(200) NOT NULL,
                       start_time TIME NOT NULL,
                       date DATE NOT NULL
);

CREATE TABLE user_participate_event (
                                        user_id INT,
                                        event_id INT,
                                        PRIMARY KEY (user_id, event_id),
                                        FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
                                        FOREIGN KEY (event_id) REFERENCES event(event_id) ON DELETE CASCADE
);

CREATE TABLE card_list (
                           card_id INT AUTO_INCREMENT PRIMARY KEY,
                           black_mana INT DEFAULT 0,
                           blue_mana INT DEFAULT 0,
                           green_mana INT DEFAULT 0,
                           red_mana INT DEFAULT 0,
                           white_mana INT DEFAULT 0,
                           neutral_mana INT DEFAULT 0,
                           name VARCHAR(100) NOT NULL,
                           super_type ENUM ('BASIC', 'LEGENDARY') DEFAULT ('BASIC'),
                           card_type ENUM ('ARTIFACT', 'CREATURE', 'ENCHANTMENT', 'INSTANT', 'KINDRED', 'LAND', 'SORCERY') NOT NULL,
                           multi_type ENUM ('ARTIFACT', 'CREATURE', 'ENCHANTMENT', 'INSTANT', 'KINDRED', 'LAND', 'SORCERY'),
                           sub_type VARCHAR(100),
                           can_be_commander BOOLEAN DEFAULT false,
                           picture VARCHAR(100) NOT NULL,
                           set_name VARCHAR(200) NOT NULL,
                           rule_text VARCHAR(1000) NOT NULL,
                           power INT DEFAULT 0,
                           toughness INT DEFAULT 0,
                           rarity ENUM ('COMMON', 'UNCOMMON', 'RARE', 'MYTHIC_RARE') NOT NULL
);

CREATE TABLE owned_card (
                            owned_card_id INT AUTO_INCREMENT PRIMARY KEY,
                            card_id INT,
                            FOREIGN KEY (card_id) REFERENCES card_list(card_id) ON DELETE CASCADE,
                            user_id INT,
                            FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
                            card_condition ENUM ('MINT', 'NEAR_MINT', 'EXCELLENT', 'GOOD', 'LIGHT_PLAYED', 'PLAYED', 'POOR', 'UNSPECIFIED') DEFAULT ('UNSPECIFIED'),
                            foil VARCHAR(100)
);

CREATE TABLE deck (
                      deck_id INT AUTO_INCREMENT PRIMARY KEY,
                      user_id INT,
                      FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
                      deck_name VARCHAR(100) DEFAULT 'Unavngivet',
                      format ENUM ('COMMANDER', 'STANDARD') NOT NULL
);

CREATE TABLE deck_contain_card (
                                   deck_contain_id INT AUTO_INCREMENT PRIMARY KEY,
                                   deck_id INT,
                                   FOREIGN KEY (deck_id) REFERENCES deck(deck_id) ON DELETE CASCADE,
                                   card_id INT,
                                   FOREIGN KEY (card_id) REFERENCES card_list(card_id) ON DELETE CASCADE,
                                   is_commander BOOLEAN DEFAULT false
);